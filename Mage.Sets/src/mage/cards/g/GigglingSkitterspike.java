package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author Cguy7777
 */
public final class GigglingSkitterspike extends CardImpl {

    public GigglingSkitterspike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.TOY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Giggling Skitterspike attacks, blocks, or becomes the target of a spell,
        // it deals damage equal to its power to each opponent.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new DamagePlayersEffect(SourcePermanentPowerValue.NOT_NEGATIVE, TargetController.OPPONENT)
                        .setText("it deals damage equal to its power to each opponent"),
                false,
                "Whenever {this} attacks, blocks, or becomes the target of a spell, ",
                new AttacksOrBlocksTriggeredAbility(null, false),
                new BecomesTargetSourceTriggeredAbility(null, StaticFilters.FILTER_SPELL_A)));

        // {5}: Monstrosity 5.
        this.addAbility(new MonstrosityAbility("{5}", 5));

    }

    private GigglingSkitterspike(final GigglingSkitterspike card) {
        super(card);
    }

    @Override
    public GigglingSkitterspike copy() {
        return new GigglingSkitterspike(this);
    }
}
