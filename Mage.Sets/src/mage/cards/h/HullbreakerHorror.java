package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HullbreakerHorror extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public HullbreakerHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(8);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Whenever you cast a spell, choose up to one —
        // • Return target spell you don't control to its owner's hand.
        Ability ability = new SpellCastControllerTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetSpell(filter));
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(1);

        // • Return target nonland permanent to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetNonlandPermanent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private HullbreakerHorror(final HullbreakerHorror card) {
        super(card);
    }

    @Override
    public HullbreakerHorror copy() {
        return new HullbreakerHorror(this);
    }
}
