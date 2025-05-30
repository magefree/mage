package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntrudingSoulrager extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ROOM, "a Room");

    public IntrudingSoulrager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}, Sacrifice a Room: Intruding Soulrager deals 2 damage to each opponent. Draw a card.
        Ability ability = new SimpleActivatedAbility(new DamagePlayersEffect(2, TargetController.OPPONENT), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private IntrudingSoulrager(final IntrudingSoulrager card) {
        super(card);
    }

    @Override
    public IntrudingSoulrager copy() {
        return new IntrudingSoulrager(this);
    }
}
