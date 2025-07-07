package mage.cards.f;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author maurer.it_at_gmail.com
 */
public final class FulgentDistraction extends CardImpl {

    public FulgentDistraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        this.getSpellAbility().addEffect(new FulgentDistractionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    private FulgentDistraction(final FulgentDistraction card) {
        super(card);
    }

    @Override
    public FulgentDistraction copy() {
        return new FulgentDistraction(this);
    }
}

class FulgentDistractionEffect extends OneShotEffect {

    FulgentDistractionEffect() {
        super(Outcome.Tap);
        staticText = "Choose two target creatures. Tap those creatures, then unattach all Equipment from them";
    }

    private FulgentDistractionEffect(FulgentDistractionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            permanent.tap(source, game);
        }
        for (Permanent permanent : permanents) {
            Set<UUID> attachments = permanent
                    .getAttachments()
                    .stream()
                    .map(game::getPermanent)
                    .filter(attachment -> attachment.hasSubtype(SubType.EQUIPMENT, game))
                    .map(MageItem::getId)
                    .collect(Collectors.toSet());
            for (UUID attachmentId : attachments) {
                permanent.removeAttachment(attachmentId, source, game);
            }
        }
        return true;
    }

    @Override
    public FulgentDistractionEffect copy() {
        return new FulgentDistractionEffect(this);
    }

}
