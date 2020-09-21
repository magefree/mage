package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlasspoolMimic extends CardImpl {

    public GlasspoolMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.g.GlasspoolShore.class;

        // You may have Glasspool Mimic enter the battlefield as a copy of a creature you control, except it's a Shapeshifter Rogue in addition to its other types.
        this.addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, new GlasspoolMimicApplier()),
                true, null, "You may have {this} enter the battlefield as a copy of " +
                "a creature you control, except it's a Shapeshifter Rogue in addition to its other types.", ""
        ));
    }

    private GlasspoolMimic(final GlasspoolMimic card) {
        super(card);
    }

    @Override
    public GlasspoolMimic copy() {
        return new GlasspoolMimic(this);
    }
}

class GlasspoolMimicApplier extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        permanent.getSubtype(game).add(SubType.SHAPESHIFTER);
        permanent.getSubtype(game).add(SubType.ROGUE);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        mageObject.getSubtype(game).add(SubType.SHAPESHIFTER);
        mageObject.getSubtype(game).add(SubType.ROGUE);
        return true;
    }
}
