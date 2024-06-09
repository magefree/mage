package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OctaviaLivingThesis extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and/or sorcery cards");
    private static final Condition condition = new CardsInControllerGraveyardCondition(8, filter);
    private static final Hint hint = new ValueHint(
            "Instants and sorceries in your graveyard", new CardsInControllerGraveyardCount(filter)
    );

    public OctaviaLivingThesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {8} less to cast if you have eight or more instant and/or sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(8, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(hint));

        // Ward {8}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{8}"), false));

        // Magecraft — Whenever you cast an instant or sorcery spell, target creature has base power and toughness 8/8 until end of turn.
        Ability ability = new MagecraftAbility(
                new SetBasePowerToughnessTargetEffect(8, 8, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private OctaviaLivingThesis(final OctaviaLivingThesis card) {
        super(card);
    }

    @Override
    public OctaviaLivingThesis copy() {
        return new OctaviaLivingThesis(this);
    }
}
