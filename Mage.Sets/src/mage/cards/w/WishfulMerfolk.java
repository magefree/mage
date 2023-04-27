package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class WishfulMerfolk extends CardImpl {

    public WishfulMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {1}{U}: Wishful Merfolk loses defender and becomes a Human until end of turn.
        this.addAbility(new SimpleActivatedAbility(new WishfulMerfolkEffect(), new ManaCostsImpl<>("{1}{U}")));
    }

    private WishfulMerfolk(final WishfulMerfolk card) {
        super(card);
    }

    @Override
    public WishfulMerfolk copy() {
        return new WishfulMerfolk(this);
    }
}

class WishfulMerfolkEffect extends ContinuousEffectImpl {

    public WishfulMerfolkEffect() {
        super(Duration.EndOfTurn, Outcome.AddAbility);
        staticText = "{this} loses defender and becomes a Human until end of turn";
    }

    public WishfulMerfolkEffect(final WishfulMerfolkEffect effect) {
        super(effect);
    }

    @Override
    public WishfulMerfolkEffect copy() {
        return new WishfulMerfolkEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
                permanent.removeAbility(DefenderAbility.getInstance(), source.getSourceId(), game);
                break;
            case TypeChangingEffects_4:
                permanent.removeAllCreatureTypes(game);
                permanent.addSubType(game, SubType.HUMAN);
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.TypeChangingEffects_4;
    }
}
