package mage.cards.a;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.*;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArlinnThePacksHope extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCreatureCard("creature spells");

    public ArlinnThePacksHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.ARLINN}, "{2}{R}{G}",
                "Arlinn, the Moon's Fury",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.ARLINN}, "RG");
        this.getLeftHalfCard().setStartingLoyalty(4);
        this.getRightHalfCard().setStartingLoyalty(4);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // +1: Until your next turn, you may cast creature spells as though they had flash, and each creature you control enters the battlefield with an additional +1/+1 counter on it.
        Ability ability = new LoyaltyAbility(new CastAsThoughItHadFlashAllEffect(
                Duration.UntilYourNextTurn, filter
        ).setText("until your next turn, you may cast creature spells as though they had flash"), 1);
        ability.addEffect(new EntersWithCountersControlledEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, CounterType.P1P1.createInstance(), false
        ).concatBy(", and"));
        this.getLeftHalfCard().addAbility(ability);

        // −3: Create two 2/2 green Wolf creature tokens.
        this.getLeftHalfCard().addAbility(new LoyaltyAbility(new CreateTokenEffect(new WolfToken(), 2), -3));

        // Arlinn, the Moon's Fury
        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());

        // +2: Add {R}{G}.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new BasicManaEffect(new Mana(
                0, 0, 0, 1, 1, 0, 0, 0
        )), 2));

        // 0: Until end of turn, Arlinn, the Moon's Fury becomes a 5/5 Werewolf creature with trample, indestructible, and haste.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new ArlinnTheMoonsFuryEffect(), 0));

    }

    private ArlinnThePacksHope(final ArlinnThePacksHope card) {
        super(card);
    }

    @Override
    public ArlinnThePacksHope copy() {
        return new ArlinnThePacksHope(this);
    }
}

class ArlinnTheMoonsFuryEffect extends ContinuousEffectImpl {

    ArlinnTheMoonsFuryEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, {this} becomes a 5/5 Werewolf creature with trample, indestructible, and haste";
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    private ArlinnTheMoonsFuryEffect(final ArlinnTheMoonsFuryEffect effect) {
        super(effect);
    }

    @Override
    public ArlinnTheMoonsFuryEffect copy() {
        return new ArlinnTheMoonsFuryEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.removeAllCreatureTypes(game);
                permanent.addSubType(game, SubType.WEREWOLF);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(TrampleAbility.getInstance(), source.getSourceId(), game);
                permanent.addAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
                permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(5);
                    permanent.getToughness().setModifiedBaseValue(5);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
