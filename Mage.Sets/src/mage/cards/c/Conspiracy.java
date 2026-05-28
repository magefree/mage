package mage.cards.c;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.ModifyObjectAllMultiZoneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreatureSpell;
import mage.filter.common.FilterOwnedCreatureCard;
import mage.util.SubTypes;

import java.util.UUID;

/**
 * @author bunchOfDevs
 */
public final class Conspiracy extends CardImpl {

    public Conspiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // As Conspiracy enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));

        // Creature cards you own that aren't on the battlefield, creature 
        // spells you control, and creatures you control are the chosen type.
        this.addAbility(new SimpleStaticAbility(new ConspiracyEffect()));

    }

    private Conspiracy(final Conspiracy card) {
        super(card);
    }

    @Override
    public Conspiracy copy() {
        return new Conspiracy(this);
    }

}

class ConspiracyEffect extends ModifyObjectAllMultiZoneEffect {

    ConspiracyEffect() {
        super(
            StaticFilters.FILTER_CONTROLLED_CREATURES,
            new FilterControlledCreatureSpell("creature spells you control"),
            new FilterOwnedCreatureCard("creature cards you own"),
            (obj, source, game) -> {
                final SubTypes subTypes = game.getState().getCreateMageObjectAttribute(obj, game).getSubtype();
                subTypes.setIsAllCreatureTypes(false);
                subTypes.removeAll(SubType.getCreatureTypes());
                subTypes.add(ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game));
            },
            "the chosen type"
        );

        this.dependendToTypes.add(DependencyType.BecomeCreature);  // Opalescence and Starfield of Nyx
    }

    private ConspiracyEffect(final ConspiracyEffect effect) {
        super(effect);
    }

    @Override
    public ConspiracyEffect copy() {
        return new ConspiracyEffect(this);
    }

}
