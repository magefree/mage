package mage.cards.p;

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
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author Loki
 */
public final class PhyrexianMetamorph extends CardImpl {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.addCardType(CardType.ARTIFACT);
            return true;
        }

        @Override
        public String getText() {
            return ", except it's an artifact in addition to its other types";
        }
    };

    public PhyrexianMetamorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // {U/P} ( can be paid with either {U} or 2 life.)
        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE, applier
        ), true));
    }

    private PhyrexianMetamorph(final PhyrexianMetamorph card) {
        super(card);
    }

    @Override
    public PhyrexianMetamorph copy() {
        return new PhyrexianMetamorph(this);
    }

}
