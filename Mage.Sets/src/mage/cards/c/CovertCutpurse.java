package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovertCutpurse extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control that was dealt damage this turn");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public CovertCutpurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.c.CovetousGeist.class;

        // When Covert Cutpurse enters the battlefield, destroy target creature you don't control that was dealt damage this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Disturb {4}{B}
        this.addAbility(new DisturbAbility(this, "{4}{B}"));
    }

    private CovertCutpurse(final CovertCutpurse card) {
        super(card);
    }

    @Override
    public CovertCutpurse copy() {
        return new CovertCutpurse(this);
    }
}
