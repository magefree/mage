package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformTargetEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProgenitorExarch extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(
            SubType.INCUBATOR, "Incubator token you control"
    );

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public ProgenitorExarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Progenitor Exarch enters the battlefield, incubate 3 X times.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ProgenitorExarchEffect()));

        // {T}: Transform target Incubator token you control.
        Ability ability = new SimpleActivatedAbility(new TransformTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ProgenitorExarch(final ProgenitorExarch card) {
        super(card);
    }

    @Override
    public ProgenitorExarch copy() {
        return new ProgenitorExarch(this);
    }
}

class ProgenitorExarchEffect extends OneShotEffect {

    ProgenitorExarchEffect() {
        super(Outcome.Benefit);
        staticText = "incubate 3 X times";
    }

    private ProgenitorExarchEffect(final ProgenitorExarchEffect effect) {
        super(effect);
    }

    @Override
    public ProgenitorExarchEffect copy() {
        return new ProgenitorExarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = ManacostVariableValue.ETB.calculate(game, source, this);
        if (xValue < 1) {
            return false;
        }
        for (int i = 0; i < xValue; i++) {
            new IncubateEffect(3).apply(game, source);
        }
        return true;
    }
}
