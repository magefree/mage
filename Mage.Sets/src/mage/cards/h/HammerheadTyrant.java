package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HammerheadTyrant extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent(
            "nonland permanent an opponent controls with mana value less than or equal to that spell's mana value"
    );

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(HammerheadTyrantPredicate.instance);
    }

    public HammerheadTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell, return up to one target nonland permanent an opponent controls with mana value less than or equal to that spell's mana value to its owner's hand.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new ReturnToHandTargetEffect(), StaticFilters.FILTER_SPELL_A, false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private HammerheadTyrant(final HammerheadTyrant card) {
        super(card);
    }

    @Override
    public HammerheadTyrant copy() {
        return new HammerheadTyrant(this);
    }
}

enum HammerheadTyrantPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return CardUtil
                .getEffectValueFromAbility(input.getSource(), "spellCast", Spell.class)
                .map(Spell::getManaValue)
                .filter(x -> x >= input.getObject().getManaValue())
                .isPresent();
    }
}
