package mage.cards.c;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class CryptOfTheEternals extends CardImpl {

    public CryptOfTheEternals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // When Crypt of the Eternals enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add {U}, {B}, or {R}.
        List<Mana> list = new ArrayList<Mana>() {{
            add(Mana.BlueMana(1));
            add(Mana.BlackMana(1));
            add(Mana.RedMana(1));
        }};
        
        for(Mana m: list) {       
            SimpleManaAbility uAbility = new SimpleManaAbility(Zone.BATTLEFIELD, m, new ManaCostsImpl("{1}"));
            uAbility.addCost(new TapSourceCost());
            this.addAbility(uAbility);
        }
    }

    public CryptOfTheEternals(final CryptOfTheEternals card) {
        super(card);
    }

    @Override
    public CryptOfTheEternals copy() {
        return new CryptOfTheEternals(this);
    }
}
