package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.ProfessorDellianFelEmblem;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfessorDellianFel extends CardImpl {

    public ProfessorDellianFel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DELLIAN);
        this.setStartingLoyalty(5);

        // +2: You gain 3 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(3), 2));

        // 0: You draw a card and lose 1 life.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1, true), 0);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));
        this.addAbility(ability);

        // -3: Destroy target creature.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -6: You get an emblem with "Whenever you gain life, target opponent loses that much life."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ProfessorDellianFelEmblem()), -6));
    }

    private ProfessorDellianFel(final ProfessorDellianFel card) {
        super(card);
    }

    @Override
    public ProfessorDellianFel copy() {
        return new ProfessorDellianFel(this);
    }
}
