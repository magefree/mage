package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.GreenCatToken;
import mage.game.permanent.token.WhiteDogToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class RinAndSeriInseparable extends CardImpl {

    private static final FilterSpell dogSpellFilter = new FilterSpell("a Dog spell");
    private static final FilterSpell catSpellFilter = new FilterSpell("a Cat spell");

    private static final FilterPermanent dogPermanentFilter = new FilterControlledCreaturePermanent("Dogs you control");
    private static final FilterPermanent catPermanentFilter = new FilterControlledCreaturePermanent("Cats you control");

    static {
        dogSpellFilter.add(SubType.DOG.getPredicate());
        catSpellFilter.add(SubType.CAT.getPredicate());

        dogPermanentFilter.add(SubType.DOG.getPredicate());
        catPermanentFilter.add(SubType.CAT.getPredicate());
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
                new CreateTokenEffect(new GreenCatToken()), dogSpellFilter, false
        ));

        // Whenever you cast a Cat spell, create a 1/1 white Dog creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new WhiteDogToken()), catSpellFilter, false
        ));

        // {R}{G}{W}, {T}: Rin and Seri, Inseparable deals damage to any target equal to the number of Dogs you control. You gain life equal to the number of Cats you control.
        DynamicValue dogCount = new PermanentsOnBattlefieldCount(dogPermanentFilter);
        Effect damageEffect = new DamageTargetEffect(dogCount);
        damageEffect.setText("{this} deals damage to any target equal to the number of Dogs you control");
        DynamicValue catCount = new PermanentsOnBattlefieldCount(catPermanentFilter);
        Effect lifeGainEffect = new GainLifeEffect(catCount);
        lifeGainEffect.setText("You gain life equal to the number of Cats you control");
        Ability ability = new SimpleActivatedAbility(damageEffect, new ManaCostsImpl<>("{R}{G}{W}"));
        ability.addEffect(lifeGainEffect);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        ability.addHint(new ValueHint("Dogs you control", dogCount));
        ability.addHint(new ValueHint("Cats you control", catCount));
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
