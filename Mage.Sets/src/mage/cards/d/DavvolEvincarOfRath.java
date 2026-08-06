package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class DavvolEvincarOfRath extends CardImpl {

    public DavvolEvincarOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature you control enters, you lose 1 life and add {B}
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
            new LoseLifeSourceControllerEffect(1),
            StaticFilters.FILTER_ANOTHER_CREATURE
        );
        ability.addEffect(new AddManaToManaPoolSourceControllerEffect(Mana.BlackMana(1)).setText("and add {B}"));
        this.addAbility(ability);
    }

    private DavvolEvincarOfRath(final DavvolEvincarOfRath card) {
        super(card);
    }

    @Override
    public DavvolEvincarOfRath copy() {
        return new DavvolEvincarOfRath(this);
    }
}
