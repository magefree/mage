package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIsActivePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.token.CatToken3;
import mage.game.permanent.token.DogToken2;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class RinAndSeriInseparable extends CardImpl {

    private static final FilterSpell dogSpellFilter = new FilterSpell("a Dog spell");
    private static final FilterSpell catSpellFilter = new FilterSpell("a Cat spell");

    private static final FilterPermanent dogPermanentFilter = new FilterCreaturePermanent("dog you control");
    private static final FilterPermanent catPermanentFilter = new FilterCreaturePermanent("cat you control");

    static {
        dogSpellFilter.add(SubType.DOG.getPredicate());
        catSpellFilter.add(SubType.CAT.getPredicate());

        dogPermanentFilter.add(SubType.DOG.getPredicate());
        dogPermanentFilter.add(new ControllerIsActivePlayerPredicate());
        catPermanentFilter.add(SubType.CAT.getPredicate());
        catPermanentFilter.add(new ControllerIsActivePlayerPredicate());
    }

    public RinAndSeriInseparable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast a Dog spell, create a 1/1 green Cat creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new CatToken3()), dogSpellFilter, false
        ));

        // Whenever you cast a Cat spell, create a 1/1 white Dog creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new DogToken2()), catSpellFilter, false
        ));

        // {R}{G}{W}: Rin and Seri, Inseparable deals damage to any target equal to the number of Dogs you control. You gain life equal to the number of Cats you control.
        Effect damageEffect = new DamageTargetEffect(new DynamicValue() {
            @Override
            public int calculate(Game game, Ability sourceAbility, Effect effect) {
                return game.getBattlefield().count(dogPermanentFilter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
            }

            @Override
            public DynamicValue copy() {
                return this;
            }

            @Override
            public String getMessage() {
                return "Dogs you control";
            }
        });
        damageEffect.setText("{source} deals damage to any target equal to the number of Dogs you control");
        Effect lifeGainEffect = new GainLifeEffect(new DynamicValue() {
            @Override
            public int calculate(Game game, Ability sourceAbility, Effect effect) {
                return game.getBattlefield().count(catPermanentFilter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
            }

            @Override
            public DynamicValue copy() {
                return this;
            }

            @Override
            public String getMessage() {
                return "Cats you control";
            }
        });
        lifeGainEffect.setText("You gain life equal to the number of Cats you control.");
        Ability ability = new SimpleActivatedAbility(damageEffect, new ManaCostsImpl("{R}{G}{W}"));
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(lifeGainEffect);
        this.addAbility(ability);
    }

    private RinAndSeriInseparable(final RinAndSeriInseparable card) {
        super(card);
    }

    @Override
    public RinAndSeriInseparable copy() {
        return new RinAndSeriInseparable(this);
    }
}
