package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.ModifyObjectAllMultiZoneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreatureSpell;
import mage.filter.common.FilterOwnedCreatureCard;
import mage.game.permanent.token.ShapeshifterBlueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaskwoodNexus extends CardImpl {

    public MaskwoodNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Creatures you control are every creature type. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        this.addAbility(new SimpleStaticAbility(new MaskwoodNexusEffect()));

        // {3}, {T}: Create a 2/2 blue Shapeshifter creature token with changeling.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ShapeshifterBlueToken()), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MaskwoodNexus(final MaskwoodNexus card) {
        super(card);
    }

    @Override
    public MaskwoodNexus copy() {
        return new MaskwoodNexus(this);
    }

}

class MaskwoodNexusEffect extends ModifyObjectAllMultiZoneEffect {

    MaskwoodNexusEffect() {
        super(
            StaticFilters.FILTER_CONTROLLED_CREATURES,
            new FilterControlledCreatureSpell("creature spells you control"),
            new FilterOwnedCreatureCard("creature cards you own"),
            (obj, source, game) -> { game.getState().getCreateMageObjectAttribute(obj, game).getSubtype().setIsAllCreatureTypes(true); },
            "every creature type"
        );
    }

    private MaskwoodNexusEffect(final MaskwoodNexusEffect effect) {
        super(effect);
    }

    @Override
    public MaskwoodNexusEffect copy() {
        return new MaskwoodNexusEffect(this);
    }

}
