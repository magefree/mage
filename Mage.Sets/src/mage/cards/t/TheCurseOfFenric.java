package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TheCurseOfFenricMutantToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author padfoothelix 
 */
public final class TheCurseOfFenric extends CardImpl {

    private static final FilterCreaturePermanent nontokenFilter = new FilterCreaturePermanent("nontoken creature");
    private static final FilterPermanent mutantFilter = new FilterPermanent(SubType.MUTANT, "mutant");
    private static final FilterCreaturePermanent fenricFilter = new FilterCreaturePermanent("creature named Fenric");

    static {
        nontokenFilter.add(TokenPredicate.FALSE);
        fenricFilter.add(new NamePredicate("Fenric"));
    }

    public TheCurseOfFenric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- For each player, destroy up to one target creature that player controls. For each creature destroyed this way, its controller creates a 3/3 green Mutant creature token with deathtouch.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                ability -> {
                    ability.addEffect(new TheCurseOfFenricDestroyEffect()
                            .setText("for each player, destroy up to one target creature " +
                                    "that player controls. For each creature destroyed " +
                                    "this way, its controller creates a 3/3 green Mutant " +
                                    "creature with deathtouch")
                            .setTargetPointer(new EachTargetPointer())
                    );
                    ability.setTargetAdjuster(TheCurseOfFenricAdjuster.instance);
                }
        );


        // II -- Target nontoken creature becomes a 6/6 legendary Horror creature named Fenric and loses all abilities.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new BecomesCreatureTargetEffect(
                        new TheCurseOfFenricHorrorToken(),
                        true, false, Duration.EndOfGame, true
                ),
                new TargetCreaturePermanent(nontokenFilter)
        );

        // III -- Target Mutant fights another target creature named Fenric.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                ability -> {
                    ability.addEffect(new FightTargetsEffect().setText(
                            "Target Mutant fight another target creature named Fenric"
                    ));
                    ability.addTarget(new TargetPermanent(mutantFilter));
                    ability.addTarget(new TargetCreaturePermanent(fenricFilter));
                }
        );

        this.addAbility(sagaAbility);
    }

    private TheCurseOfFenric(final TheCurseOfFenric card) {
        super(card);
    }

    @Override
    public TheCurseOfFenric copy() {
        return new TheCurseOfFenric(this);
    }
}

class TheCurseOfFenricDestroyEffect extends OneShotEffect {

    public TheCurseOfFenricDestroyEffect() {
        super(Outcome.DestroyPermanent);
    }

    private TheCurseOfFenricDestroyEffect(final TheCurseOfFenricDestroyEffect effect) {
        super(effect);
    }

    @Override
    public TheCurseOfFenricDestroyEffect copy() {
        return new TheCurseOfFenricDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token mutantToken = new TheCurseOfFenricMutantToken();
        for (Target target : source.getTargets()) {
            Permanent targetCreature = game.getPermanent(target.getFirstTarget());
            if (targetCreature != null) {
                UUID controllerId = targetCreature.getControllerId();
                if (targetCreature.destroy(source, game, false)) {
                    mutantToken.putOntoBattlefield(1, game, source, controllerId);            
                }
            }
        }
        return true;
    }
}

enum TheCurseOfFenricAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent(
                    "creature "
                            + (ability.isControlledBy(playerId) ? "you control" : "controlled by " + player.getName())
            );
            filter.add(new ControllerIdPredicate(playerId));
            ability.addTarget(new TargetPermanent(0, 1, filter));
        }
    }
}

class TheCurseOfFenricHorrorToken extends TokenImpl {

    TheCurseOfFenricHorrorToken() {
        super("Fenric", "6/6 legendary Horror creature named Fenric");
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
    }

    private TheCurseOfFenricHorrorToken(final TheCurseOfFenricHorrorToken token) {
        super(token);
    }

    public TheCurseOfFenricHorrorToken copy() {
        return new TheCurseOfFenricHorrorToken(this);
    }
}
