/*
 * Copyright (C) 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.genesis.sahx.fmogithub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutPageFragment extends Fragment {
	private RecyclerView mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (RecyclerView) inflater.inflate(R.layout.fragment_page, container, false);
		return mRootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initRecyclerView();
	}

	private void initRecyclerView() {
		mRootView.setAdapter(new AboutPageAdapter(1));
	}

	//return a new instance of the about page fragment
	public static Fragment newInstance() {
		return new AboutPageFragment();
	}

	private static class AboutPageAdapter extends RecyclerView.Adapter<AboutPageVH> {
		private final int numItems;

		AboutPageAdapter(int numItems) {
			this.numItems = numItems;
		}

		@Override
		public AboutPageVH onCreateViewHolder(ViewGroup viewGroup, int i) {
			View itemView = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.list_item_card, viewGroup, false);

			return new AboutPageVH(itemView);
		}

		@Override
		public void onBindViewHolder(AboutPageVH aboutPageVH, int i) {
			// do nothing
		}

		@Override
		public int getItemCount() {
			return numItems;
		}
	}

	private static class AboutPageVH extends RecyclerView.ViewHolder {
		AboutPageVH(View itemView) {
			super(itemView);
		}
	}
}
