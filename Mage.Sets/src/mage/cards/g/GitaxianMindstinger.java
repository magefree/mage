package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GitaxianMindstinger extends CardImpl {

    public GitaxianMindstinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Gitaxian Mindstinger deals combat damage to a player or battle, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(new DrawCardSourceControllerEffect(1),false));
    }

    private GitaxianMindstinger(final GitaxianMindstinger card) {
        super(card);
    }

    @Override
    public GitaxianMindstinger copy() {
        return new GitaxianMindstinger(this);
    }
}
