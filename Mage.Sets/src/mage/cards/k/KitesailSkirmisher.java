package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KitesailSkirmisher extends CardImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("another target creature attacking the same player or planeswalker");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(KitesailSkirmisherPredicate.instance);
    }

    public KitesailSkirmisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Kitesail Skirmisher attacks, another target creature attacking the same player or planeswalker gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Encore {4}{U}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{4}{U}")));
    }

    private KitesailSkirmisher(final KitesailSkirmisher card) {
        super(card);
    }

    @Override
    public KitesailSkirmisher copy() {
        return new KitesailSkirmisher(this);
    }
}

enum KitesailSkirmisherPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return Objects.equals(
                game.getCombat().getDefenderId(input.getSourceId()),
                game.getCombat().getDefenderId(input.getObject().getId())
        );
    }
}