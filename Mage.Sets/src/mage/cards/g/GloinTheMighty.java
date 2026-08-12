package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GloinTheMighty extends AdventureCard {

    public GloinTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{3}{R}", "Easy Pickings", "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // At the beginning of your first main phase, add {R}{R}.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
            new AddManaToManaPoolSourceControllerEffect(Mana.RedMana(2))
        ));

        // Easy Pickings
        // Easy Pickings deals 1 damage to each creature your opponents control.
        this.getSpellCard().getSpellAbility().addEffect(new DamageAllEffect(
            1,
            new FilterOpponentsCreaturePermanent("creature your opponents control")
        ));

        this.finalizeAdventure();
    }

    private GloinTheMighty(final GloinTheMighty card) {
        super(card);
    }

    @Override
    public GloinTheMighty copy() {
        return new GloinTheMighty(this);
    }
}
