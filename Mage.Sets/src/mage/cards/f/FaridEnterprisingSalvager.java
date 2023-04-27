package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ScrapToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class FaridEnterprisingSalvager extends CardImpl {

    private static final FilterPermanent filter
            =new FilterControlledArtifactPermanent("a nontoken artifact you control");static {filter.add(TokenPredicate.FALSE);}
    public FaridEnterprisingSalvager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a nontoken artifact you control is put into a graveyard from the battlefield, create a colorless artifact token named Scrap.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new CreateTokenEffect(new ScrapToken()),false,filter,false));

        // {1}{R}, Sacrifice an artifact: Choose one --
        // * Put a +1/+1 counter on Farid. It gains menace until end of turn.
        Ability ability=new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),new ManaCostsImpl<>("{1}{R}"));ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(false), Duration.EndOfTurn).setText("it gains menace until end of turn"));

        // * Goad target creature.
        ability.addMode(new Mode(new GoadTargetEffect()).addTarget(new TargetCreaturePermanent()));

        // * Discard a card, then draw a card.
        ability.addMode(new Mode(new DiscardControllerEffect(1)).addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then")));
        this.addAbility(ability);
    }

    private FaridEnterprisingSalvager(final FaridEnterprisingSalvager card) {
        super(card);
    }

    @Override
    public FaridEnterprisingSalvager copy() {
        return new FaridEnterprisingSalvager(this);
    }
}
