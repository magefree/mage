package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HulkingMetamorph extends CardImpl {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.addCardType(CardType.ARTIFACT);
            blueprint.addCardType(CardType.CREATURE);
            Permanent permanent = game.getPermanentEntering(copyToObjectId);
            if (permanent != null) {
                blueprint.getPower().setModifiedBaseValue(permanent.getPower().getValue());
                blueprint.getToughness().setModifiedBaseValue(permanent.getToughness().getValue());
            }
            return true;
        }
    };

    public HulkingMetamorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Prototype {2}{U}{U} -- 3/3
        this.addAbility(new PrototypeAbility(this, "{2}{U}{U}", 3, 3));

        // You may have Hulking Metamorph enter the battlefield as a copy of an artifact or creature you control, except it's an artifact creature in addition to its other types, and its power and toughness are equal to Hulking Metamorph's power and toughness.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new EntersBattlefieldEffect(new CopyPermanentEffect(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE, applier
                ).setText("you may have {this} enter the battlefield as a copy of an artifact " +
                        "or creature you control, except it's an artifact creature in addition to its other types, " +
                        "and its power and toughness are equal to {this}'s power and toughness"), "", true))
        );
    }

    private HulkingMetamorph(final HulkingMetamorph card) {
        super(card);
    }

    @Override
    public HulkingMetamorph copy() {
        return new HulkingMetamorph(this);
    }
}
