package mage.cards.i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.filter.StaticFilters;

public final class ImmortusMasterOfEternity extends CardImpl {

    public ImmortusMasterOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {U} for each card you've drawn this turn. Spend this mana only to cast noncreature spells.
        this.addAbility(new SimpleManaAbility(new ImmortusMasterOfEternityManaEffect(), new TapSourceCost())
            .addHint(CardsDrawnThisTurnDynamicValue.getHint()));

        // Power-up -- {5}{U}{U}: Each player shuffles their hand and graveyard into their library, then draws seven cards. Put a +1/+1 counter on Immortus.
        final Ability ability = new PowerUpAbility(
            new ShuffleHandGraveyardAllEffect(),
            new ManaCostsImpl<>("{5}{U}{U}")
        );
        ability.addEffect(new DrawCardAllEffect(7).setText(", then draws seven cards"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private ImmortusMasterOfEternity(final ImmortusMasterOfEternity card) {
        super(card);
    }

    @Override
    public ImmortusMasterOfEternity copy() {
        return new ImmortusMasterOfEternity(this);
    }
}

class ImmortusMasterOfEternityManaEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder
            = new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELLS_NON_CREATURE);

    ImmortusMasterOfEternityManaEffect() {
        this.staticText = "Add {U} for each card you've drawn this turn. " + this.manaBuilder.getRule();
    }

    private ImmortusMasterOfEternityManaEffect(final ImmortusMasterOfEternityManaEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game == null) {
            return new ArrayList<>();
        }
        return new ArrayList<Mana>(
            Collections.singletonList(
                this.manaBuilder.setMana(
                    Mana.BlueMana(
                        CardsDrawnThisTurnDynamicValue.instance.calculate(game, source, null)
                    ), source, game
                ).build()
            )
        );
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }
        return
            this.manaBuilder.setMana(
                Mana.BlueMana(
                    CardsDrawnThisTurnDynamicValue.instance.calculate(game, source, null)
                ), source, game
            ).build();
    }

    @Override
    public ImmortusMasterOfEternityManaEffect copy() {
        return new ImmortusMasterOfEternityManaEffect(this);
    }
}
