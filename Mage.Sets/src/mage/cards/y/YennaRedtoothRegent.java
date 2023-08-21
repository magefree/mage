
package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class YennaRedtoothRegent extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledEnchantmentPermanent(
                    "enchantment you control that doesn't have the "
                            + "same name as another permanent you control"
            );

    static {
        filter.add(YennaRedtoothRegentPredicate.instance);
    }

    public YennaRedtoothRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}, {T}: Choose target enchantment you control that doesn't have the same name as another permanent you control. Create a token that's a copy of it, except it isn't legendary. If the token is an Aura, untap Yenna, Redtooth Regent, then scry 2. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new YennaRedtoothRegentEffect(),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private YennaRedtoothRegent(final YennaRedtoothRegent card) {
        super(card);
    }

    @Override
    public YennaRedtoothRegent copy() {
        return new YennaRedtoothRegent(this);
    }
}

enum YennaRedtoothRegentPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        String name = input.getObject().getName();
        if (name == null || name.isEmpty()) {
            return true;
        }

        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(new NamePredicate(name));
        return game.getBattlefield().count(filter, input.getPlayerId(), input.getSource(), game) == 1;
    }
}

class YennaRedtoothRegentEffect extends OneShotEffect {

    YennaRedtoothRegentEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Choose target enchantment you control that doesn't have the same name as another "
                + "permanent you control. Create a token that's a copy of it, except it isn't legendary"
                + "If the token is an Aura, untap {this}, then scry 2";
    }

    private YennaRedtoothRegentEffect(final YennaRedtoothRegentEffect effect) {
        super(effect);
    }

    @Override
    public YennaRedtoothRegentEffect copy() {
        return new YennaRedtoothRegentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect create = new CreateTokenCopyTargetEffect()
                .setPermanentModifier((token) -> token.removeSuperType(SuperType.LEGENDARY));

        if (!create.apply(game, source)) {
            return false;
        }

        Permanent token = create.getAddedPermanents().get(0);
        Player controller = game.getPlayer(source.getControllerId());
        if (token == null || !token.hasSubtype(SubType.AURA, game)) {
            return true;
        }

        Permanent yenna = game.getPermanent(source.getSourceId());
        if (yenna != null) {
            yenna.untap(game);
        }
        if (controller != null) {
            controller.scry(2, source, game);
        }
        return true;
    }
}