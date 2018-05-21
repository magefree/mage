/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.command.emblems.WillKenrithEmblem;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class WillKenrith extends CardImpl {

    public WillKenrith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WILL);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +2: Until your next turn, up to two target creatures each have base power and toughness 0/3 and lose all abilities.
        Ability ability = new LoyaltyAbility(
                new SetPowerToughnessTargetEffect(0, 3, Duration.EndOfTurn)
                        .setText("until end of turn, up to two target creatures each have base power and toughness 0/3"),
                2
        );
        ability.addEffect(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn)
                .setText("and lose all abilities")
        );
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // -2: Target player draws two cards. Until your next turn, instant, sorcery, and planeswalker spells that player casts cost 2 less to cast.
        ability = new LoyaltyAbility(new DrawCardTargetEffect(2), -2);
        ability.addEffect(new WillKenrithCostReductionEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -8: Target player gets an emblem with "Whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy."
        Effect effect = new GetEmblemTargetPlayerEffect(new WillKenrithEmblem());
        effect.setText(
                "Target player gets an emblem with "
                + "\"Whenever you cast an instant or sorcery spell, "
                + "copy it. You may choose new targets for the copy.\""
        );
        ability = new LoyaltyAbility(effect, -8);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Partner with Rowan Kenrith
        this.addAbility(new PartnerWithAbility("Rowan Kenrith", true, false));

        // Will Kenrith can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    public WillKenrith(final WillKenrith card) {
        super(card);
    }

    @Override
    public WillKenrith copy() {
        return new WillKenrith(this);
    }
}

class WillKenrithCostReductionEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY),
                new CardTypePredicate(CardType.PLANESWALKER)
        ));
    }

    WillKenrithCostReductionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Until your next turn, instant, sorcery, and planeswalker spells that player casts cost {2} less to cast.";
    }

    WillKenrithCostReductionEffect(final WillKenrithCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public WillKenrithCostReductionEffect copy() {
        return new WillKenrithCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter2 = filter.copy();
        filter2.add(new ControllerIdPredicate(source.getFirstTarget()));
        ContinuousEffect effect = new SpellsCostReductionAllEffect(filter2, 2);
        effect.setDuration(Duration.UntilYourNextTurn);
        game.addEffect(effect, source);
        return true;
    }
}
