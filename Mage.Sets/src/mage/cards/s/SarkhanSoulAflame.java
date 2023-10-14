package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarkhanSoulAflame extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dragon spells you cast");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.DRAGON, "a Dragon");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public SarkhanSoulAflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Dragon spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever a Dragon enters the battlefield under your control, you may have Sarkhan, Soul Aflame become a copy of it until end of turn, except its name is Sarkhan, Soul Aflame and it's legendary in addition to its other types.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new SarkhanSoulAflameEffect(), filter2, true
        ));
    }

    private SarkhanSoulAflame(final SarkhanSoulAflame card) {
        super(card);
    }

    @Override
    public SarkhanSoulAflame copy() {
        return new SarkhanSoulAflame(this);
    }
}

class SarkhanSoulAflameEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.setName("Sarkhan, Soul Aflame");
            blueprint.addSuperType(SuperType.LEGENDARY);
            return true;
        }
    };

    SarkhanSoulAflameEffect() {
        super(Outcome.Benefit);
        staticText = "have {this} become a copy of it until end of turn, " +
                "except its name is Sarkhan, Soul Aflame and it's legendary in addition to its other types";
    }

    private SarkhanSoulAflameEffect(final SarkhanSoulAflameEffect effect) {
        super(effect);
    }

    @Override
    public SarkhanSoulAflameEffect copy() {
        return new SarkhanSoulAflameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null || creature == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, creature, source.getSourceId(), source, applier);
        return true;
    }
}
