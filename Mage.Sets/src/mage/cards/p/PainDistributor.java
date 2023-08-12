package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.StackObject;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PainDistributor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("their first spell each turn");
    private static final FilterPermanent filter2 = new FilterArtifactPermanent("an artifact an opponent controls");

    static {
        filter.add(PainDistributorPredicate.instance);
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PainDistributor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever a player casts their first spell each turn, they create a Treasure token.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new CreateTokenTargetEffect(new TreasureToken())
                        .setText("they create a Treasure token"),
                filter, false, SetTargetPointer.PLAYER
        ), new SpellsCastWatcher());

        // Whenever an artifact an opponent controls is put into a graveyard from the battlefield, Pain Distributor deals 1 damage to that player.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new PainDistributorEffect(), false, filter2, false
        ));
    }

    private PainDistributor(final PainDistributor card) {
        super(card);
    }

    @Override
    public PainDistributor copy() {
        return new PainDistributor(this);
    }
}

enum PainDistributorPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return game.getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(input.getControllerId())
                .size() == 1;
    }
}

class PainDistributorEffect extends OneShotEffect {

    PainDistributorEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to that player";
    }

    private PainDistributorEffect(final PainDistributorEffect effect) {
        super(effect);
    }

    @Override
    public PainDistributorEffect copy() {
        return new PainDistributorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(getValue("permanentDied"))
                .filter(Objects::nonNull)
                .map(Permanent.class::cast)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player.damage(1, source, game) > 0)
                .orElse(false);
    }
}
