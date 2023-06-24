package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureDiedControlledCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BontuTheGlorified extends CardImpl {

    public BontuTheGlorified(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Bontu the Glorified can't attack or block unless a creature died under your control this turn.
        this.addAbility(new SimpleStaticAbility(new BontuTheGlorifiedRestrictionEffect()).addHint(CreatureDiedControlledCondition.getHint()));

        // {1}{B}, Sacrifice another creature: Scry 1.  Each opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(
                new ScryEffect(1, false), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addEffect(new LoseLifeOpponentsEffect(1));
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);

    }

    private BontuTheGlorified(final BontuTheGlorified card) {
        super(card);
    }

    @Override
    public BontuTheGlorified copy() {
        return new BontuTheGlorified(this);
    }
}

class BontuTheGlorifiedRestrictionEffect extends RestrictionEffect {

    public BontuTheGlorifiedRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless a creature died under your control this turn";
    }

    public BontuTheGlorifiedRestrictionEffect(final BontuTheGlorifiedRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public BontuTheGlorifiedRestrictionEffect copy() {
        return new BontuTheGlorifiedRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
            if (controller != null
                    && watcher != null) {
                return (watcher.getAmountOfCreaturesDiedThisTurnByController(controller.getId()) == 0);
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}
