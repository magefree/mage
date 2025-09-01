package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KimahriValiantGuardian extends CardImpl {

    public KimahriValiantGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ronso Rage -- At the beginning of combat on your turn, put a +1/+1 counter on Kimahri and tap target creature an opponent controls. Then you may have Kimahri become a copy of that creature, except its name is Kimahri, Valiant Guardian and it has vigilance and this ability.
        this.addAbility(makeAbility());
    }

    private KimahriValiantGuardian(final KimahriValiantGuardian card) {
        super(card);
    }

    @Override
    public KimahriValiantGuardian copy() {
        return new KimahriValiantGuardian(this);
    }

    static Ability makeAbility() {
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        );
        ability.addEffect(new TapTargetEffect().concatBy("and"));
        ability.addEffect(new KimahriValiantGuardianEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        return ability.withFlavorWord("Ronso Rage");
    }
}

class KimahriValiantGuardianEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.setName("Kimahri, Valiant Guardian");
            blueprint.getAbilities().add(VigilanceAbility.getInstance());
            blueprint.getAbilities().add(KimahriValiantGuardian.makeAbility());
            return true;
        }
    };

    KimahriValiantGuardianEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may have {this} become a copy of that creature, " +
                "except its name is Kimahri, Valiant Guardian and it has vigilance and this ability.";
    }

    private KimahriValiantGuardianEffect(final KimahriValiantGuardianEffect effect) {
        super(effect);
    }

    @Override
    public KimahriValiantGuardianEffect copy() {
        return new KimahriValiantGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null
                && permanent != null
                && creature != null
                && player.chooseUse(Outcome.Copy, "Have " + permanent.getIdName() +
                " become a copy of " + creature.getIdName() + '?', source, game)
                && game.copyPermanent(Duration.Custom, creature, permanent.getId(), source, applier) != null;
    }
}
