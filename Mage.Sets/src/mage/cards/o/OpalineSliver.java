package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class OpalineSliver extends CardImpl {

    private static final FilterPermanent filterSliver = new FilterPermanent("All Slivers");
    private static final FilterSpell filterSpell = new FilterSpell("a spell an opponent controls");

    static {
        filterSliver.add(SubType.SLIVER.getPredicate());
        filterSpell.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public OpalineSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "Whenever this permanent becomes the target of a spell an opponent controls, you may draw a card."
        Ability gainedTriggeredAbility = new BecomesTargetSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filterSpell, SetTargetPointer.NONE, true)
                .setTriggerPhrase("Whenever this permanent becomes the target of a spell an opponent controls, ");
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                gainedTriggeredAbility, Duration.WhileOnBattlefield, filterSliver,
                "All Slivers have \"Whenever this permanent becomes the target of a spell an opponent controls, you may draw a card.\"")));
    }

    private OpalineSliver(final OpalineSliver card) {
        super(card);
    }

    @Override
    public OpalineSliver copy() {
        return new OpalineSliver(this);
    }
}
