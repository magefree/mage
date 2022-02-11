package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Alvin
 */
public final class CurseOfTheBloodyTome extends CardImpl {

    public CurseOfTheBloodyTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer target = new TargetPlayer();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(target.getTargetName());
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, that player puts the top two cards of their library into their graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new PutLibraryIntoGraveTargetEffect(2)
                        .setText("that player mills two cards"),
                TargetController.ENCHANTED, false
        ));
    }

    private CurseOfTheBloodyTome(final CurseOfTheBloodyTome card) {
        super(card);
    }

    @Override
    public CurseOfTheBloodyTome copy() {
        return new CurseOfTheBloodyTome(this);
    }
}
