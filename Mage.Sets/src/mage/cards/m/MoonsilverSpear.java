package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.AngelToken;

/**
 *
 * @author Loki
 */
public final class MoonsilverSpear extends CardImpl {

    public MoonsilverSpear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Whenever equipped creature attacks, create a 4/4 white Angel creature token with flying.
        this.addAbility(new AttacksAttachedTriggeredAbility(new CreateTokenEffect(new AngelToken())));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4), false));
    }

    private MoonsilverSpear(final MoonsilverSpear card) {
        super(card);
    }

    @Override
    public MoonsilverSpear copy() {
        return new MoonsilverSpear(this);
    }
}
