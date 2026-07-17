package mage.abilities.effects.common.continuous;

import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.*;

import java.util.Optional;

/**
 * @author TheElk801, Susucr
 */
public class AddCreatureSubTypeAllMultiZoneEffect extends ModifyObjectAllMultiZoneEffect {
    private final FilterControlledCreaturePermanent filterPermanent;
    private final FilterControlledCreatureSpell filterSpell;
    private final FilterOwnedCreatureCard filterCard;
    private final SubType chosenType;

    public AddCreatureSubTypeAllMultiZoneEffect() {
        this((SubType)null);
    }

    public AddCreatureSubTypeAllMultiZoneEffect(SubType chosenType) {
        this(
            StaticFilters.FILTER_CONTROLLED_CREATURES,
            new FilterControlledCreatureSpell("creature spells you control"),
            new FilterOwnedCreatureCard("creature cards you own"),
            chosenType
        );
    }

    public AddCreatureSubTypeAllMultiZoneEffect(
            FilterControlledCreaturePermanent filterPermanent,
            FilterControlledCreatureSpell filterSpell,
            FilterOwnedCreatureCard filterCard
    ) {
        this(filterPermanent, filterSpell, filterCard, null);
    }

    public AddCreatureSubTypeAllMultiZoneEffect(
            FilterControlledCreaturePermanent filterPermanent,
            FilterControlledCreatureSpell filterSpell,
            FilterOwnedCreatureCard filterCard,
            SubType chosenType
    ) {
        super(filterPermanent, filterSpell, filterCard,
            chosenType == null ?
                (obj, source, game) -> {
                    Optional.ofNullable(
                        ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game)
                    ).ifPresent(type -> {
                        game.getState().getCreateMageObjectAttribute(obj, game).getSubtype().add(type);
                    });
                } :
                (obj, source, game) -> { game.getState().getCreateMageObjectAttribute(obj, game).getSubtype().add(chosenType); },
            (chosenType == null ? "the chosen type" : chosenType.getPluralName()) + " in addition to their other types"
        );

        this.filterPermanent = filterPermanent;
        this.filterSpell = filterSpell;
        this.filterCard = filterCard;
        this.chosenType = chosenType;

        this.dependendToTypes.add(DependencyType.BecomeCreature);  // Opalescence and Starfield of Nyx
    }

    private AddCreatureSubTypeAllMultiZoneEffect(final AddCreatureSubTypeAllMultiZoneEffect effect) {
        super(effect);
        this.filterPermanent = effect.filterPermanent;
        this.filterSpell = effect.filterSpell;
        this.filterCard = effect.filterCard;
        this.chosenType = effect.chosenType;
    }

    @Override
    public AddCreatureSubTypeAllMultiZoneEffect copy() {
        return new AddCreatureSubTypeAllMultiZoneEffect(this);
    }
}
