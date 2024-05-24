package mage.cards.e;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllControllerEffect;
import mage.abilities.effects.common.continuous.GainControlAllControlledTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EmrakulTheWorldAnew extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("spells");
    private static final FilterPermanent filterPermanent = new FilterPermanent();

    static {
        filterPermanent.add(EmrakulTheWorldAnewPredicate.instance);
    }

    public EmrakulTheWorldAnew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{12}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // When you cast this spell, gain control of all creatures target player controls.
        Ability ability = new CastSourceTriggeredAbility(
                new GainControlAllControlledTargetEffect(StaticFilters.FILTER_PERMANENT_CREATURES)
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // protection from spells and from permanents that were cast this turn
        this.addAbility(new ProtectionAbility(filterSpell));
        this.addAbility(new ProtectionAbility(filterPermanent)
                .setText("Protection from permanents that were cast this turn"));

        // When Emrakul, the World Anew leaves the battlefield, sacrifice all creatures you control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SacrificeAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURES), false));

        // Madness--Pay six {C}.
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{C}{C}{C}{C}{C}{C}").setText("—Pay six {C}.")));

    }

    private EmrakulTheWorldAnew(final EmrakulTheWorldAnew card) {
        super(card);
    }

    @Override
    public EmrakulTheWorldAnew copy() {
        return new EmrakulTheWorldAnew(this);
    }
}

/**
 * permanent was cast this turn
 */
enum EmrakulTheWorldAnewPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        MageObjectReference mor = new MageObjectReference(input, game, -1);
        return watcher != null && watcher
                .getAllSpellsCastThisTurn()
                .anyMatch(spell -> mor.refersTo(spell, game));
    }
}
