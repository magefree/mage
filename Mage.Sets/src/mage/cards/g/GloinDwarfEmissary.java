package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterHistoricSpell;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloinDwarfEmissary extends CardImpl {

    private static final FilterSpell filter = new FilterHistoricSpell();
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.TREASURE, "a Treasure");

    public GloinDwarfEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a historic spell, create a Treasure token. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), filter, false
        ).setTriggersOnceEachTurn(true));

        // {T}, Sacrifice a Treasure: Goad target creature.
        Ability ability = new SimpleActivatedAbility(new GoadTargetEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter2));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GloinDwarfEmissary(final GloinDwarfEmissary card) {
        super(card);
    }

    @Override
    public GloinDwarfEmissary copy() {
        return new GloinDwarfEmissary(this);
    }
}
