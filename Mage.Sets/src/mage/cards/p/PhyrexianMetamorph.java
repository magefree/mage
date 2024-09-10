package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class PhyrexianMetamorph extends CardImpl {

    public PhyrexianMetamorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        CopyApplier phyrexianMetamorphCopyApplier = new CopyApplier() {
            @Override
            public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
                blueprint.addCardType(CardType.ARTIFACT);
                return true;
            }
        };

        // {U/P} ( can be paid with either {U} or 2 life.)
        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE, phyrexianMetamorphCopyApplier);
        effect.setText("You may have {this} enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types");
        Ability ability = new SimpleStaticAbility(Zone.ALL, new EntersBattlefieldEffect(effect, "", true));
        this.addAbility(ability);
    }

    private PhyrexianMetamorph(final PhyrexianMetamorph card) {
        super(card);
    }

    @Override
    public PhyrexianMetamorph copy() {
        return new PhyrexianMetamorph(this);
    }

}
