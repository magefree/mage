package mage.cards.a;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArlinnTheMoonsFury extends CardImpl {

    public ArlinnTheMoonsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARLINN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));
        this.color.setRed(true);
        this.color.setGreen(true);
        this.transformable = true;
        this.nightCard = true;

        // Nightbound
        this.addAbility(new NightboundAbility());

        // +2: Add {R}{G}.
        this.addAbility(new LoyaltyAbility(new BasicManaEffect(new Mana(
                0, 0, 0, 1, 1, 0, 0, 0
        )), 2));

        // 0: Until end of turn, Arlinn, the Moon's Fury becomes a 5/5 Werewolf creature with trample, indestructible, and haste.
        this.addAbility(new LoyaltyAbility(new ArlinnTheMoonsFuryEffect(), 0));
    }

    private ArlinnTheMoonsFury(final ArlinnTheMoonsFury card) {
        super(card);
    }

    @Override
    public ArlinnTheMoonsFury copy() {
        return new ArlinnTheMoonsFury(this);
    }
}

class ArlinnTheMoonsFuryEffect extends ContinuousEffectImpl {

    ArlinnTheMoonsFuryEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, {this} becomes a 5/5 Werewolf creature with trample, indestructible, and haste";
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
                    permanent.getPower().setValue(5);
                    permanent.getToughness().setValue(5);
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
