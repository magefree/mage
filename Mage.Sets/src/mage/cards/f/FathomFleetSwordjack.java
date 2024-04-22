package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FathomFleetSwordjack extends CardImpl {

    public FathomFleetSwordjack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Fathom Fleet Swordjack attacks, it deals damage to the player or planeswalker it's attacking equal to the number of artifacts you control.
        this.addAbility(new AttacksTriggeredAbility(new FathomFleetSwordjackEffect(), false));

        // Encore {5}{R}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{5}{R}")));
    }

    private FathomFleetSwordjack(final FathomFleetSwordjack card) {
        super(card);
    }

    @Override
    public FathomFleetSwordjack copy() {
        return new FathomFleetSwordjack(this);
    }
}

class FathomFleetSwordjackEffect extends OneShotEffect {

    FathomFleetSwordjackEffect() {
        super(Outcome.Benefit);
        staticText = "it deals damage to the player or planeswalker " +
                "it's attacking equal to the number of artifacts you control";
    }

    private FathomFleetSwordjackEffect(final FathomFleetSwordjackEffect effect) {
        super(effect);
    }

    @Override
    public FathomFleetSwordjackEffect copy() {
        return new FathomFleetSwordjackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int artifactCount = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                source.getControllerId(), source, game
        );
        return artifactCount > 0 && game.damagePlayerOrPermanent(
                game.getCombat().getDefenderId(source.getSourceId()), artifactCount,
                source.getSourceId(), source, game, false, true
        ) > 0;
    }
}
