package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MagusOfTheVineyard extends CardImpl {

    public MagusOfTheVineyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of each player's precombat main phase, add {G}{G} to that player's mana pool.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(2), "that player's"),
                TargetController.EACH_PLAYER, false));
    }

    private MagusOfTheVineyard(final MagusOfTheVineyard card) {
        super(card);
    }

    @Override
    public MagusOfTheVineyard copy() {
        return new MagusOfTheVineyard(this);
    }
}
