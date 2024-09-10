package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThaliaAndTheGitrogMonster extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("creatures and nonbasic lands your opponents control");
    private static final FilterPermanent filter2 = new FilterPermanent("a creature or land");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                Predicates.and(
                        Predicates.not(SuperType.BASIC.getPredicate()),
                        CardType.LAND.getPredicate()
                )
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter2.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public ThaliaAndTheGitrogMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        // Creatures and nonbasic lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));

        // Whenever Thalia and The Gitrog Monster attacks, sacrifice a creature or land, then draw a card.
        Ability ability = new AttacksTriggeredAbility(new SacrificeControllerEffect(filter2, 1, null));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);
    }

    private ThaliaAndTheGitrogMonster(final ThaliaAndTheGitrogMonster card) {
        super(card);
    }

    @Override
    public ThaliaAndTheGitrogMonster copy() {
        return new ThaliaAndTheGitrogMonster(this);
    }
}
