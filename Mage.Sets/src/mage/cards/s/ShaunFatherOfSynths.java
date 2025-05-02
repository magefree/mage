package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author alexander-novo
 */
public class ShaunFatherOfSynths extends CardImpl {

    private static final FilterControlledCreaturePermanent attackFilter = new FilterControlledCreaturePermanent(
            "attacking legendary creature you control other than {this}");
    private static final FilterControlledPermanent exileFilter = new FilterControlledPermanent(SubType.SYNTH,
            "Synth tokens you control");

    static {
        attackFilter.add(AnotherPredicate.instance);
        attackFilter.add(AttackingPredicate.instance);
        attackFilter.add(SuperType.LEGENDARY.getPredicate());

        exileFilter.add(TokenPredicate.TRUE);
    }

    public ShaunFatherOfSynths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you attack, you may create a tapped and attacking token that's a copy of target attacking legendary creature you control other than Shaun, except it's not legendary and it's a Synth artifact creature in addition to its other types.
        Effect effect = new CreateTokenCopyTargetEffect(
                null, null,
                false, 1, true, true)
                .setPermanentModifier(token -> {
                    token.removeSuperType(SuperType.LEGENDARY);
                    token.addCardType(CardType.CREATURE);
                    token.addCardType(CardType.ARTIFACT);
                    token.addSubType(SubType.SYNTH);
                }).setText(
                        "create a tapped and attacking token that's a copy of target attacking legendary creature you control other than Shaun, except it's not legendary and it's a Synth artifact creature in addition to its other types");
        TriggeredAbility ability = new AttacksWithCreaturesTriggeredAbility(effect, 1);
        ability.addTarget(new TargetPermanent(attackFilter));
        ability.setOptional();
        this.addAbility(ability);

        // When Shaun leaves the battlefield, exile all Synth tokens you control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ExileAllEffect(exileFilter), false));
    }

    private ShaunFatherOfSynths(final ShaunFatherOfSynths card) {
        super(card);
    }

    @Override
    public ShaunFatherOfSynths copy() {
        return new ShaunFatherOfSynths(this);
    }

}
