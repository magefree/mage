package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.FaerieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlelaArtfulProvocateur extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures you control with flying");
    private static final FilterSpell filter2 = new FilterSpell("an artifact or enchantment spell");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public AlelaArtfulProvocateur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Other creatures you control with flying get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 0, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever you cast an artifact or enchantment spell, create a 1/1 blue Faerie creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new FaerieToken()), filter2, false
        ));
    }

    private AlelaArtfulProvocateur(final AlelaArtfulProvocateur card) {
        super(card);
    }

    @Override
    public AlelaArtfulProvocateur copy() {
        return new AlelaArtfulProvocateur(this);
    }
}
