
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PossessedNomad extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public PossessedNomad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Threshold - As long as seven or more cards are in your graveyard, Possessed Nomad gets +1/+1, is black, and has "{2}{B}, {tap}: Destroy target white creature."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), new CardsInControllerGraveyardCondition(7),
            "As long as seven or more cards are in your graveyard, {this} gets +1/+1"));

        Effect effect = new ConditionalContinuousEffect(new BecomesColorSourceEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield),
            new CardsInControllerGraveyardCondition(7), ", is black");
        ability.addEffect(effect);

        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        gainedAbility.addCost(new TapSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(filter));
        effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(gainedAbility),
            new CardsInControllerGraveyardCondition(7), ", and has \"{2}{B}, {T}: Destroy target white creature.\"");
        ability.addEffect(effect);

        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private PossessedNomad(final PossessedNomad card) {
        super(card);
    }

    @Override
    public PossessedNomad copy() {
        return new PossessedNomad(this);
    }
}
