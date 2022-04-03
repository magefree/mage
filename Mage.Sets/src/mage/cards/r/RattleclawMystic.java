
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RattleclawMystic extends CardImpl {

    public RattleclawMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {G}, {U}, or {R}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // Morph {2}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}")));
        
        // When Rattleclaw Mystic is turned face up, add {G}{U}{R}.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(new Mana(0, 1, 0, 1,1, 0,0, 0))));

    }

    private RattleclawMystic(final RattleclawMystic card) {
        super(card);
    }

    @Override
    public RattleclawMystic copy() {
        return new RattleclawMystic(this);
    }
}
