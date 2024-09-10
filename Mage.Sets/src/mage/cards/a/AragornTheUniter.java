package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AragornTheUniter extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");
    private static final FilterSpell filter2 = new FilterSpell("a blue spell");
    private static final FilterSpell filter3 = new FilterSpell("a red spell");
    private static final FilterSpell filter4 = new FilterSpell("a green spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
        filter3.add(new ColorPredicate(ObjectColor.RED));
        filter4.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public AragornTheUniter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever you cast a white spell, create a 1/1 white Human Soldier creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanSoldierToken()), filter, false
        ));

        // Whenever you cast a blue spell, scry 2.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ScryEffect(2, false), filter2, false
        ));

        // Whenever you cast a red spell, Aragorn, the Uniter deals 3 damage to target opponent.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(3), filter3, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever you cast a green spell, target creature gets +4/+4 until end of turn.
        ability = new SpellCastControllerTriggeredAbility(
                new BoostTargetEffect(4, 4), filter4, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AragornTheUniter(final AragornTheUniter card) {
        super(card);
    }

    @Override
    public AragornTheUniter copy() {
        return new AragornTheUniter(this);
    }
}
