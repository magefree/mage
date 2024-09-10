package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GandalfTheGrey extends CardImpl {

    private static final FilterSpell filter
            = new FilterInstantOrSorcerySpell("instant or sorcery spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public GandalfTheGrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell, choose one that hasn't been chosen --
        // * You may tap or untap target permanent.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new MayTapOrUntapTargetEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.setModeTag("tap or untap");
        ability.addTarget(new TargetPermanent());
        ability.getModes().setLimitUsageByOnce(false);

        // * Gandalf the Grey deals 3 damage to each opponent.
        ability.addMode(
                new Mode(new DamagePlayersEffect(3, TargetController.OPPONENT))
                        .setModeTag("damage opponents")
        );

        // * Copy target instant or sorcery spell you control. You may choose new targets for the copy.
        ability.addMode(
                new Mode(new CopyTargetStackObjectEffect()).addTarget(new TargetSpell(filter))
                        .setModeTag("copy spell")
        );

        // * Put Gandalf on top of its owner's library.
        ability.addMode(
                new Mode(new PutOnLibrarySourceEffect(true))
                        .setModeTag("put on top of library")
        );

        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private GandalfTheGrey(final GandalfTheGrey card) {
        super(card);
    }

    @Override
    public GandalfTheGrey copy() {
        return new GandalfTheGrey(this);
    }
}
