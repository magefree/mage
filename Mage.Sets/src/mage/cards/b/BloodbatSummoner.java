package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodbatSummoner extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.BLOOD, "Blood token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public BloodbatSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, up to one target Blood token you control becomes a 2/2 black Bat creature with flying and haste in addition to its other types.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(2, 2, "", SubType.BAT)
                        .withAbility(FlyingAbility.getInstance())
                        .withAbility(HasteAbility.getInstance())
                        .withColor("B"),
                false, false, Duration.Custom
        ).setText("up to one target Blood token you control becomes a " +
                "2/2 black Bat creature with flying and haste in addition to its other types"), TargetController.YOU, false);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private BloodbatSummoner(final BloodbatSummoner card) {
        super(card);
    }

    @Override
    public BloodbatSummoner copy() {
        return new BloodbatSummoner(this);
    }
}
