package mage.cards.a;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author sobiech
 */
public final class AdaptiveOmnitool extends CardImpl {

    private final static DynamicValue artifactYouControlCount = new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent());

    public AdaptiveOmnitool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each artifact you control.
        this.addAbility(
                new SimpleStaticAbility(new BoostEquippedEffect(artifactYouControlCount, artifactYouControlCount)).addHint(ArtifactYouControlHint.instance)
        );

        // Whenever equipped creature attacks, look at the top six cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new LookLibraryAndPickControllerEffect(
                        6,
                        1,
                        StaticFilters.FILTER_CARD_ARTIFACT,
                        PutCards.HAND,
                        PutCards.BOTTOM_RANDOM
                )
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));
    }

    private AdaptiveOmnitool(final AdaptiveOmnitool card) {
        super(card);
    }

    @Override
    public AdaptiveOmnitool copy() {
        return new AdaptiveOmnitool(this);
    }
}
