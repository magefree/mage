package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainFlashbackTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KatildaAndLier extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a Human spell");

    static {
        filterSpell.add(SubType.HUMAN.getPredicate());
    }

    public KatildaAndLier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Human spell, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        Ability ability = new SpellCastControllerTriggeredAbility(new GainFlashbackTargetEffect(), filterSpell, false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(ability);
    }

    private KatildaAndLier(final KatildaAndLier card) {
        super(card);
    }

    @Override
    public KatildaAndLier copy() {
        return new KatildaAndLier(this);
    }
}
