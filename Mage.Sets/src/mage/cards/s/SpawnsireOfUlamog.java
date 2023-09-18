
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CastCardFromOutsideTheGameEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 * @author magenoxx_at_gmail.com
 */
public final class SpawnsireOfUlamog extends CardImpl {
    
    private static final String ruleText = "Cast any number of Eldrazi cards you own from outside the game without paying their mana costs";

    private static final FilterCard filter = new FilterCard("Eldrazi cards");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
    }
    
    public SpawnsireOfUlamog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{10}");
        this.subtype.add(SubType.ELDRAZI);

        this.power = new MageInt(7);
        this.toughness = new MageInt(11);

        // Annihilator 1
        this.addAbility(new AnnihilatorAbility(1));

        // {4}: Create two 0/1 colorless Eldrazi Spawn creature tokens. They have "Sacrifice this creature: Add {C}."
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new EldraziSpawnToken(), 2), new GenericManaCost(4)));

        // {20}: Cast any number of Eldrazi cards you own from outside the game without paying their mana costs.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CastCardFromOutsideTheGameEffect(filter, ruleText), new GenericManaCost(20)
        ).addHint(OpenSideboardHint.instance));
    }

    private SpawnsireOfUlamog(final SpawnsireOfUlamog card) {
        super(card);
    }

    @Override
    public SpawnsireOfUlamog copy() {
        return new SpawnsireOfUlamog(this);
    }
}
