package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class NivMizzetGhostCounsel extends CardImpl {

    public NivMizzetGhostCounsel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT, SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you gain life, you may pay that much life. If you do, draw that many cards.
        this.addAbility(new GainLifeControllerTriggeredAbility(new NivMizzetGhostCounselEffect(), false, true));

        // {T}: Each opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(1), new TapSourceCost());
        ability.addEffect(new GainLifeEffect(1).setText("and you gain 1 life"));
        this.addAbility(ability);
    }

    private NivMizzetGhostCounsel(final NivMizzetGhostCounsel card) {
        super(card);
    }

    @Override
    public NivMizzetGhostCounsel copy() {
        return new NivMizzetGhostCounsel(this);
    }
}

class NivMizzetGhostCounselEffect extends OneShotEffect {

    public NivMizzetGhostCounselEffect() {
        super(Outcome.DrawCard);
        staticText = "you may pay that much life. If you do, draw that many cards";
    }

    private NivMizzetGhostCounselEffect(final NivMizzetGhostCounselEffect effect) {
        super(effect);
    }

    @Override
    public NivMizzetGhostCounselEffect copy() {
        return new NivMizzetGhostCounselEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lifeGained = SavedGainedLifeValue.MANY.calculate(game, source, this);
        Effect effect = new DoIfCostPaid(new DrawCardSourceControllerEffect(lifeGained).setText("draw that many cards"), new PayLifeCost(lifeGained));
        effect.apply(game, source);
        return true;
    }
}
