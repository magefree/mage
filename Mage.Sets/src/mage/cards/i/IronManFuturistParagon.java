package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class IronManFuturistParagon extends CardImpl {

    public IronManFuturistParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, target artifact or creature becomes an artifact creature with base power and toughness 5/5 and gains flying.
        Ability ability = new BeginningOfCombatTriggeredAbility(
            new BecomesCreatureTargetEffect(
                new CreatureToken(5, 5).withType(CardType.ARTIFACT).withAbility(FlyingAbility.getInstance()),
                false,
                false,
                Duration.Custom
            ).setText("target artifact or creature becomes an artifact creature with base power and toughness 5/5 and gains flying")
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private IronManFuturistParagon(final IronManFuturistParagon card) {
        super(card);
    }

    @Override
    public IronManFuturistParagon copy() {
        return new IronManFuturistParagon(this);
    }
}
