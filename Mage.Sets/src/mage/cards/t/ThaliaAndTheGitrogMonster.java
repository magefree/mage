package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

public class ThaliaAndTheGitrogMonster extends CardImpl {

    private static final FilterPermanent filterTapped = new FilterPermanent("creatures and nonbasic lands your opponents control");
    private static final FilterControlledPermanent filterAttack = new FilterControlledPermanent("creature or land");

    static {
        filterAttack.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
        filterTapped.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
        filterTapped.add(Predicates.not(
                SuperType.BASIC.getPredicate()
        ));
        filterTapped.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ThaliaAndTheGitrogMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.FROG);
        this.addSubType(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        //Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        //You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        //Creatures and nonbasic lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(
                new PermanentsEnterBattlefieldTappedEffect(filterTapped)
        ));

        //Whenever Thalia and The Gitrog Monster attacks, sacrifice a creature or land, then draw a card.
        AttacksTriggeredAbility attacksTriggeredAbility = new AttacksTriggeredAbility(new SacrificeTargetEffect(),
                false, "Whenever Thalia and The Gitrog Monster attacks, sacrifice a creature or land, " +
                "then draw a card.");
        attacksTriggeredAbility.addTarget(new TargetControlledPermanent(filterAttack));
        attacksTriggeredAbility.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(attacksTriggeredAbility);
    }

    private ThaliaAndTheGitrogMonster(final ThaliaAndTheGitrogMonster card) {
        super(card);
    }

    @Override
    public ThaliaAndTheGitrogMonster copy() {
        return new ThaliaAndTheGitrogMonster(this);
    }
}
